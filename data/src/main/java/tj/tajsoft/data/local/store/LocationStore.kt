package tj.tajsoft.data.local.store

import javax.inject.Inject

class LocationStore @Inject constructor(): BaseStore<String>("location", String::class.java) {
}