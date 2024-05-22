package tj.tajsoft.data.local.store

import javax.inject.Inject

class BirthDayStore @Inject constructor(): BaseStore<String>("birth_day", String::class.java) {
}