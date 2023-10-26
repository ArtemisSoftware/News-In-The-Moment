package util

import androidx.compose.ui.input.pointer.PointerIcon
import java.awt.Cursor

object CursorUtils {
    fun handCursor() = PointerIcon(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR))
}