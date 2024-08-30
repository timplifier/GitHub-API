package com.presidentServiceConsult.githubAPI.data.local

import android.content.SharedPreferences
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class Preferences(val preferences: SharedPreferences) : KoinComponent {

    protected val json by inject<Json>()

    protected fun boolean(
        default: Boolean = false,
        key: String? = null
    ): ReadWriteProperty<Any, Boolean> =
        create(default, key, preferences::getBoolean, preferences.edit()::putBoolean)

    protected fun int(default: Int = 0, key: String? = null): ReadWriteProperty<Any, Int> =
        create(default, key, preferences::getInt, preferences.edit()::putInt)

    protected fun float(default: Float = 0f, key: String? = null): ReadWriteProperty<Any, Float> =
        create(default, key, preferences::getFloat, preferences.edit()::putFloat)

    protected fun long(default: Long = 0L, key: String? = null): ReadWriteProperty<Any, Long> =
        create(default, key, preferences::getLong, preferences.edit()::putLong)

    protected fun string(
        default: String = "",
        key: String? = null
    ): ReadWriteProperty<Any, String> =
        create(
            default,
            key,
            { k, d -> preferences.getString(k, d) as String },
            preferences.edit()::putString
        )

    protected fun stringSet(
        default: Set<String> = emptySet(),
        key: String? = null
    ): ReadWriteProperty<Any, Set<String>> = create(
        default,
        key,
        { k, d -> preferences.getStringSet(k, d) as Set<String> },
        preferences.edit()::putStringSet
    )

    protected inline fun <reified T> nonPrimitive(
        default: T? = null,
        key: String? = null
    ): ReadWriteProperty<Any, T?> = create(
        default,
        key,
        { k, _ -> json.decodeFromString(preferences.getString(k, "") as String) }, { k, v ->
            preferences.edit().putString(k, json.encodeToString(v))
        }
    )

    fun <T> create(
        default: T,
        key: String? = null,
        getter: (key: String, default: T) -> T,
        setter: (key: String, value: T) -> SharedPreferences.Editor
    ) = object : ReadWriteProperty<Any, T> {
        private fun key(property: KProperty<*>) = key ?: property.name

        override fun getValue(thisRef: Any, property: KProperty<*>): T =
            getter(key(property), default)

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) =
            setter(key(property), value).apply()
    }
}