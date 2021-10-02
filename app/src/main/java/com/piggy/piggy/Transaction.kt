package com.piggy.piggy

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
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

    private var typeDescription: String = TransactionType.CASH.name
}
