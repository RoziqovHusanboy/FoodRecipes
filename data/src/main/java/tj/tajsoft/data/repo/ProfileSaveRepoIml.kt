package android.kurs.foodrecipes.data.repo

import tj.tajsoft.data.local.store.BirthDayStore
import tj.tajsoft.data.local.store.FullNameStore
import tj.tajsoft.data.local.store.LocationStore
import tj.tajsoft.data.local.store.PhoneNumberStore
import tj.tajsoft.data.local.store.ProfileImageStore
import tj.tajsoft.domain.repo.ProfileSaveRepo
import javax.inject.Inject
class ProfileSaveRepoIml @Inject constructor(
    private val phoneNumberStore: PhoneNumberStore,
    private val fullNameStore: FullNameStore,
    private val locationStore: LocationStore,
    private val birthDayStore: BirthDayStore,
    private val profileImageStore: ProfileImageStore
): ProfileSaveRepo {
    override suspend fun savePhoneNumber(phone: String){
        phoneNumberStore.clear()
        phoneNumberStore.set(phone)
    }

    override suspend fun saveFullName(fullName: String) {
        fullNameStore.clear()
        fullNameStore.set(fullName)
    }

    override suspend fun saveLocation(location: String) {
        locationStore.clear()
        locationStore.set(location)
    }

    override suspend fun saveBirthDay(birth: String)  {
        birthDayStore.clear()
        birthDayStore.set(birth)
    }
    override suspend fun saveImage(image: String)  {
        profileImageStore.clear()
        profileImageStore.set(image)
    }
    override suspend fun getImage(): String {
         return profileImageStore.get()?: throw IllegalStateException("Profile image not found")
    }

    override suspend fun getPhoneNumber(): String {
         return phoneNumberStore.get()?:throw IllegalStateException("Phone Number not found")
    }

    override suspend fun getFullName(): String {
        return fullNameStore.get()?:throw IllegalStateException("Full Name not found")
    }

    override suspend fun getLocation(): String {
        return locationStore.get()?:throw IllegalStateException("Location not found")
    }

    override suspend fun getBirthDay(): String {
        return birthDayStore.get()?:throw IllegalStateException("Birth day not found")
    }
}

