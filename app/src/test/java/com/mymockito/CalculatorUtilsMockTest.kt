package com.mymockito

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

/*
Simular clases o complementos que necesite la clase principal para ejecutar las pruebas:
class CalculatorUtils(private val operations: Operations, private val listener: OnResolveListener)
"CalculatorUtils" necesita de "Operations" y "OnResolveListener"
*/

@RunWith(MockitoJUnitRunner::class)
class CalculatorUtilsMockTest {
    @Mock
    lateinit var operations: Operations

    @Mock
    lateinit var listener: OnResolveListener

    lateinit var calculatorUtils: CalculatorUtils

    @Before
    fun setup(){
        calculatorUtils = CalculatorUtils(operations, listener)
    }

    @Test
    fun cacl_callCheckOrResolve_noReturn(){
        val operation = "-5x2.5"
        val isFromResolve = true
        calculatorUtils.checkOrResolve(operation, isFromResolve)
        Mockito.verify(operations).tryResolve(operation, isFromResolve, listener)
    }
}