// FIR_IDENTICAL
// DIAGNOSTICS: +ENUM_CLASS_IN_EXTERNAL_DECLARATION_WARNING
open class A

interface I

external open class B

external class <!EXTERNAL_TYPE_EXTENDS_NON_EXTERNAL_TYPE!>C<!> : A

external class <!EXTERNAL_TYPE_EXTENDS_NON_EXTERNAL_TYPE!>D<!> : B, I

external interface <!EXTERNAL_TYPE_EXTENDS_NON_EXTERNAL_TYPE!>K<!> : I

external enum class <!ENUM_CLASS_IN_EXTERNAL_DECLARATION_WARNING!>E<!> {
    X
}

external enum class <!ENUM_CLASS_IN_EXTERNAL_DECLARATION_WARNING, EXTERNAL_TYPE_EXTENDS_NON_EXTERNAL_TYPE!>F<!> : I {
    X
}