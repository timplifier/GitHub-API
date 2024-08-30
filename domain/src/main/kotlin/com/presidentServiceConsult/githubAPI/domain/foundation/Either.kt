package com.presidentServiceConsult.githubAPI.domain.foundation

import com.presidentServiceConsult.githubAPI.domain.foundation.Either.Left
import com.presidentServiceConsult.githubAPI.domain.foundation.Either.Right

sealed class Either<out A, out B> {
    fun unwrap(left: (Left<A>.() -> Unit)? = null, right: (Right<B>.() -> Unit)? = null) {
        when (this) {
            is Left -> left?.invoke(this)
            is Right -> right?.invoke(this)
        }
    }

    class Left<out A>(val value: A) : Either<A, Nothing>()

    class Right<out B>(val value: B) : Either<Nothing, B>()
}

fun <A, B> Either<A, B>.isErrorPresent(): Boolean {
    return when (this) {
        is Left ->
            true

        is Right ->
            false
    }
}