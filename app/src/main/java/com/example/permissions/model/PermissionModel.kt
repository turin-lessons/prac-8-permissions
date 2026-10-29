package com.example.permissions.model

class PermissionModel {
    private var denials = 0
    var granted = false
        private set

    val rationale: Boolean
        get() = !granted && denials == 1

    val dialogShows: Boolean
        get() = !granted && denials < 2

    fun request(allow: Boolean): String {
        if (!dialogShows) return "no dialog, denied immediately"
        if (allow) {
            granted = true
        } else {
            denials++
        }
        return "dialog shown, " + if (allow) "ALLOW" else "DENY"
    }

    override fun toString() = "granted=$granted rationale=$rationale dialogShows=$dialogShows"
}