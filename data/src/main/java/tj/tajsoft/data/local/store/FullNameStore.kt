package tj.tajsoft.data.local.store

import javax.inject.Inject

class FullNameStore @Inject constructor(): BaseStore<String>("full_name", String::class.java) {
}