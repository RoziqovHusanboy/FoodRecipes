package tj.tajsoft.data.local.store

import javax.inject.Inject

class ProfileImageStore @Inject constructor(): BaseStore<String>("image", String::class.java) {
}