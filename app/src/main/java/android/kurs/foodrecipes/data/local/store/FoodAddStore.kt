package android.kurs.foodrecipes.data.local.store

import android.kurs.foodrecipes.data.local.model.FoodAddModel
import javax.inject.Inject

class FoodAddStore @Inject constructor() :BaseStore<FoodAddModel>("food_add",FoodAddModel::class.java)