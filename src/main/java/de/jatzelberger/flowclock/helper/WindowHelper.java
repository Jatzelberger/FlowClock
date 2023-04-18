package de.jatzelberger.flowclock.helper;

import com.sun.glass.ui.Window;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

import java.util.List;

public class WindowHelper {
    public static void setWindowLong(String windowName, int windowLong) {
        final User32 user32 = User32.INSTANCE;
        Window matchingWindow = getWindowByName(windowName, Window.getWindows());
        if (matchingWindow != null) {
            final WinDef.HWND hwnd = new WinDef.HWND(new Pointer(matchingWindow.getNativeWindow()));
            user32.SetWindowLong(hwnd, WinUser.GWL_EXSTYLE, user32.GetWindowLong(hwnd, WinUser.GWL_EXSTYLE) | windowLong);
        }
    }

    private static Window getWindowByName(String windowName, List<Window> windowList) {
        for (Window window : windowList) {
            if (window.getTitle().equals(windowName)) return window;
        }
        return null;
    }
}
