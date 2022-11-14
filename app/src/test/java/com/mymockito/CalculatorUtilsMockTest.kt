package com.mymockito

import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
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
}