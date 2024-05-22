package tj.tajsoft.data.local.store

import javax.inject.Inject

class onBoardingStore @Inject constructor(): BaseStore<Boolean>("onBoarding", Boolean::class.java) {
}