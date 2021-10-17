package com.piggy.piggy

import android.os.Build
import androidx.annotation.RequiresApi
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

open class Transaction : RealmObject() {
    @PrimaryKey
    var id: UUID = UUID.randomUUID()

    @Required
    lateinit var title: String

    var amount: Int = 0

    var type: TransactionType
        get() { return TransactionType.valueOf(typeDescription) }
        set(newEnum) { typeDescription = newEnum.name }

    var category: TransactionCategory
        get() { return TransactionCategory.valueOf(categoryDescription) }
        set(newEnum) { typeDescription = newEnum.name }

    var typeDescription: String = TransactionType.EXPENSE.name

    var categoryDescription: String = TransactionCategory.CARD.name

    @RequiresApi(Build.VERSION_CODES.O)
    private var createdAt: Long = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)

    @RequiresApi(Build.VERSION_CODES.O)
    private var updatedAt: Long = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)

    @RequiresApi(Build.VERSION_CODES.O)
    fun createdAt(): Long {
        return this.createdAt
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updatedAt(): Long {
        return this.updatedAt
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun update() {
        this.updatedAt = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
    }

}
