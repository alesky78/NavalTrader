package test.displaymode;
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

/*
  test @(#)DisplayModeTest.java	1.4 01/07/17
  @bug 4189326
  @summary Tests changing display mode
  @author martak@eng: area=FullScreen
  @ignore This test enters full-screen mode, if available, and should not
  be run as an applet or as part of the test harness.
*/

/**
 * This test generates a table of all available display modes, enters
 * full-screen mode, if available, and allows you to change the display mode.
 * The application should look fine under each enumerated display mode.
 * On UNIX, only a single display mode should be available, and on Microsoft
 * Windows, display modes should depend on direct draw availability and the
 * type of graphics card.
 */

import java.awt.DisplayMode;

import javax.swing.table.DefaultTableModel;

class DisplayModeModel extends DefaultTableModel {
    private DisplayMode[] modes;

    public DisplayModeModel(DisplayMode[] modes) {
        this.modes = modes;
    }

    public DisplayMode getDisplayMode(int r) {
        return modes[r];
    }

    public String getColumnName(int c) {
        return DisplayModeTest.COLUMN_NAMES[c];
    }

    public int getColumnCount() {
        return DisplayModeTest.COLUMN_WIDTHS.length;
    }

    public boolean isCellEditable(int r, int c) {
        return false;
    }

    public int getRowCount() {
        if (modes == null) {
            return 0;
        }
        return modes.length;
    }

    public Object getValueAt(int rowIndex, int colIndex) {
        DisplayMode dm = modes[rowIndex];
        switch (colIndex) {
            case DisplayModeTest.INDEX_WIDTH :
                return Integer.toString(dm.getWidth());
            case DisplayModeTest.INDEX_HEIGHT :
                return Integer.toString(dm.getHeight());
            case DisplayModeTest.INDEX_BITDEPTH : {
                int bitDepth = dm.getBitDepth();
                String ret;
                if (bitDepth == DisplayMode.BIT_DEPTH_MULTI) {
                    ret = "Multi";
                } else {
                    ret = Integer.toString(bitDepth);
                }
                return ret;
            }
            case DisplayModeTest.INDEX_REFRESHRATE : {
                int refreshRate = dm.getRefreshRate();
                String ret;
                if (refreshRate == DisplayMode.REFRESH_RATE_UNKNOWN) {
                    ret = "Unknown";
                } else {
                    ret = Integer.toString(refreshRate);
                }
                return ret;
            }
        }
        throw new ArrayIndexOutOfBoundsException("Invalid column value");
    }

}


