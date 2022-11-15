package com.mymockito

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
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

    @Test
    fun calc_callAddOperator_validSub_noReturn(){
        val operator = "-"
        val operation = "4+" //4+-3
        var isCorrect = false
        calculatorUtils.addOperator(operator, operation){
            isCorrect = true
        }
        assertTrue(isCorrect)  //valor esperado
    }

    @Test
     fun calc_callAddOperator_invalidSub_noReturn() {
        val operator = "-"
        val operation = "4+." //4+.3
        var isCorrect = false
        calculatorUtils.addOperator(operator, operation){
            isCorrect = true
        }
        assertFalse(isCorrect)  //valor esperado
     }

    @Test
    fun calc_callAddPoint_firstPoint_noReturns(){
        val operation = "3x2"
        var isCorrect = false
        calculatorUtils.addPoint(operation){
            isCorrect = true
        }
        //valor esperado
        assertTrue(isCorrect)
        //verificacion que no haya interacciones con operaciones
        Mockito.verifyNoInteractions(operations)
    }

    @Test
    fun calc_callAddPoint_secondPoint_noReturns(){
        val operation = "3.5x2"
        val operator = "x"
        var isCorrect = false

        //indicar cual es la respuesta que quiero
        Mockito.`when`(operations.getOperator(operation)).thenReturn("x")
        Mockito.`when`(operations.divideOperation(operator, operation)).thenReturn(arrayOf("3.5", "2"))

        calculatorUtils.addPoint(operation){
            isCorrect = true
        }
        //valor esperado + verificaciones (que se ejecuten las operaciones get/divide)
        assertTrue(isCorrect)
        Mockito.verify(operations).getOperator(operation)
        Mockito.verify(operations).divideOperation(operator, operation)
    }
}