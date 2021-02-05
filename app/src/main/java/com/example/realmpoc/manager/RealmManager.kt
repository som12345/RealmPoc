package com.example.realmpoc.manager

import io.realm.Realm

class RealmManager {
    companion object {
        private val localRealms: ThreadLocal<Realm?> = ThreadLocal<Realm?>()

        /**
         * Opens a reference-counted local Realm instance.
         *
         * @return the open Realm instance
         */
        fun openLocalInstance(): Realm {
            val realm: Realm = Realm.getDefaultInstance() // <-- maybe this should be configurable
            if (localRealms.get() == null) {
                localRealms.set(realm)
            }
            return realm
        }

        /**
         * Returns the local Realm instance without adding to the reference count.
         *
         * @return the local Realm instance
         * @throws IllegalStateException when no Realm is open
         */
        val localInstance: Realm
            get() = localRealms.get()
                ?: throw IllegalStateException(
                    "No open Realms were found on this thread."
                )

        /**
         * Closes local Realm instance, decrementing the reference count.
         *
         * @throws IllegalStateException if there is no open Realm.
         */
        fun closeLocalInstance() {
            val realm: Realm = localRealms.get()
                ?: throw IllegalStateException(
                    "Cannot close a Realm that is not open."
                )
            realm.close()
            // noinspection ConstantConditions
            if (Realm.getLocalInstanceCount(Realm.getDefaultConfiguration()) <= 0) {
                localRealms.set(null)
            }
        }

        private fun checkDefaultConfiguration() {
            checkNotNull(Realm.getDefaultConfiguration()) { "No default configuration is set." }
        }
    }
}