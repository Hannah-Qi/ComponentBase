package com.example.annotation_processor.factory

import com.example.annotation_processor.config.PARAMETER_METHOD_NAME
import com.example.annotation_processor.config.PARAMETER_TARGET_NAME
import com.example.annotation_processor.config.STRING_PACKAGE_NAME
import com.squareup.kotlinpoet.ANY
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import javax.annotation.processing.Messager
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeKind

class ParamenterFactory(builder: Builder) {
    private var method: FunSpec.Builder ?= null

    private var typeElement: TypeElement?= null

    init {
        this.typeElement = builder.typeElement

        method = FunSpec.builder(PARAMETER_METHOD_NAME)
            .addModifiers(KModifier.OVERRIDE)
            .addParameter(PARAMETER_TARGET_NAME, ANY)
    }

    fun addFirstLineCode() {
        typeElement?.let { method?.addStatement("var t = $PARAMETER_TARGET_NAME as %T", it.asType())?.addStatement("val i = t.intent") }
    }

    fun buildOtherLineCode(element: Element) {
        val type = element.asType().kind.ordinal
        val getExtra = when(type) {
            TypeKind.INT.ordinal -> "getIntExtra(\"${element.simpleName}\", 0)"
            TypeKind.BOOLEAN.ordinal -> "getBooleanExtra(\"${element.simpleName}\", false)"
            else -> {
                if(element.asType().toString().equals(STRING_PACKAGE_NAME)){
                    "getStringExtra(\"${element.simpleName}\") ?: \"\""
                } else{
                    ""
                }
            }
        }
        method?.addStatement("t.${element.simpleName} = i.$getExtra")
    }

    fun build(): FunSpec {
        return method!!.build()
    }

    object Builder {
        var parameterSpec: ParameterSpec ?= null

        var typeElement: TypeElement ?= null

        fun addParameterSpec(parameterSpec: ParameterSpec): Builder {
            this.parameterSpec = parameterSpec
            return this
        }

        fun setTypeElement(typeElement: TypeElement): Builder {
            this.typeElement = typeElement
            return this
        }

        fun build(): ParamenterFactory {
            return ParamenterFactory(this)
        }
    }
}