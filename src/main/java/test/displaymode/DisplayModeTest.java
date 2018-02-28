package test.displaymode;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

public class DisplayModeTest extends JFrame implements ActionListener,
    ListSelectionListener {

    private GraphicsDevice device;
    private DisplayMode originalDM;
    private JButton exit = new JButton("Exit");
    private JButton changeDM = new JButton("Set Display");
    private JLabel currentDM = new JLabel();
    private JTable dmList = new JTable();
    private JScrollPane dmPane = new JScrollPane(dmList);
    private boolean isFullScreen = false;

    public static final int INDEX_WIDTH = 0;
    public static final int INDEX_HEIGHT = 1;
    public static final int INDEX_BITDEPTH = 2;
    public static final int INDEX_REFRESHRATE = 3;

    public static final int[] COLUMN_WIDTHS = new int[] {
        100, 100, 100, 100
    };
    public static final String[] COLUMN_NAMES = new String[] {
        "Width", "Height", "Bit Depth", "Refresh Rate"
    };

    public DisplayModeTest(GraphicsDevice device) {
        super(device.getDefaultConfiguration());
        this.device = device;
        setTitle("Display Mode Test");
        originalDM = device.getDisplayMode();
        setDMLabel(originalDM);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Make sure a DM is always selected in the list
        exit.addActionListener(this);
        changeDM.addActionListener(this);
        changeDM.setEnabled(device.isDisplayChangeSupported());
    }

    public void actionPerformed(ActionEvent ev) {
        Object source = ev.getSource();
        if (source == exit) {
            device.setDisplayMode(originalDM);
            System.exit(0);
        } else { // if (source == changeDM)
            int index = dmList.getSelectionModel().getAnchorSelectionIndex();
            if (index >= 0) {
                DisplayModeModel model = (DisplayModeModel)dmList.getModel();
                DisplayMode dm = model.getDisplayMode(index);
                device.setDisplayMode(dm);
                setDMLabel(dm);
                setSize(new Dimension(dm.getWidth(), dm.getHeight()));
                validate();
            }
        }
    }

    public void valueChanged(ListSelectionEvent ev) {
        changeDM.setEnabled(device.isDisplayChangeSupported());
    }

    private void initComponents(Container c) {
        setContentPane(c);
        c.setLayout(new BorderLayout());
        // Current DM
        JPanel currentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        c.add(currentPanel, BorderLayout.NORTH);
        JLabel current = new JLabel("Current Display Mode : ");
        currentPanel.add(current);
        currentPanel.add(currentDM);
        // Display Modes
        JPanel modesPanel = new JPanel(new GridLayout(1, 2));
        c.add(modesPanel, BorderLayout.CENTER);
        // List of display modes
        for (int i = 0; i < COLUMN_WIDTHS.length; i++) {
            TableColumn col = new TableColumn(i, COLUMN_WIDTHS[i]);
            col.setIdentifier(COLUMN_NAMES[i]);
            col.setHeaderValue(COLUMN_NAMES[i]);
            dmList.addColumn(col);
        }
        dmList.getSelectionModel().setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION);
        dmList.getSelectionModel().addListSelectionListener(this);
        modesPanel.add(dmPane);
        // Controls
        JPanel controlsPanelA = new JPanel(new BorderLayout());
        modesPanel.add(controlsPanelA);
        JPanel controlsPanelB = new JPanel(new GridLayout(2, 1));
        controlsPanelA.add(controlsPanelB, BorderLayout.NORTH);
        // Exit
        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        controlsPanelB.add(exitPanel);
        exitPanel.add(exit);
        // Change DM
        JPanel changeDMPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        controlsPanelB.add(changeDMPanel);
        changeDMPanel.add(changeDM);
        controlsPanelA.add(new JPanel(), BorderLayout.CENTER);
    }

    public void setVisible(boolean isVis) {
        super.setVisible(isVis);
        if (isVis) {
            dmList.setModel(new DisplayModeModel(device.getDisplayModes()));
        }
    }

    public void setDMLabel(DisplayMode newMode) {
        int bitDepth = newMode.getBitDepth();
        int refreshRate = newMode.getRefreshRate();
        String bd, rr;
        if (bitDepth == DisplayMode.BIT_DEPTH_MULTI) {
            bd = "Multi";
        } else {
            bd = Integer.toString(bitDepth);
        }
        if (refreshRate == DisplayMode.REFRESH_RATE_UNKNOWN) {
            rr = "Unknown";
        } else {
            rr = Integer.toString(refreshRate);
        }
        currentDM.setText(
            COLUMN_NAMES[INDEX_WIDTH] + ": " + newMode.getWidth() + " "
            + COLUMN_NAMES[INDEX_HEIGHT] + ": " + newMode.getHeight() + " "
            + COLUMN_NAMES[INDEX_BITDEPTH] + ": " + bd + " "
            + COLUMN_NAMES[INDEX_REFRESHRATE] + ": " + rr
            );
    }

    public void begin() {
        isFullScreen = device.isFullScreenSupported();
        setUndecorated(isFullScreen);
        setResizable(!isFullScreen);
        if (isFullScreen) {
            // Full-screen mode
            device.setFullScreenWindow(this);
            validate();
        } else {
            // Windowed mode
            pack();
            setVisible(true);
        }
    }

    public static void main(String[] args) {
        GraphicsEnvironment env = GraphicsEnvironment.
            getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = env.getScreenDevices();
        // REMIND : Multi-monitor full-screen mode not yet supported
        for (int i = 0; i < 1 /* devices.length */; i++) {
            DisplayModeTest test = new DisplayModeTest(devices[i]);
            test.initComponents(test.getContentPane());
            test.begin();
        }
    }
    
}