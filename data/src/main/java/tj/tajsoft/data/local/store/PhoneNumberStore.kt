package tj.tajsoft.data.local.store

import javax.inject.Inject

class PhoneNumberStore @Inject constructor(): BaseStore<String>("phone_number", String::class.java) {
}