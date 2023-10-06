package cc.ykong.lyred

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent

var service: Clicker? = null

class Clicker : AccessibilityService() {
    override fun onAccessibilityEvent(p0: AccessibilityEvent?) {}
    override fun onInterrupt() {}
    override fun onServiceConnected() {
        super.onServiceConnected()
        service = this
    }
}