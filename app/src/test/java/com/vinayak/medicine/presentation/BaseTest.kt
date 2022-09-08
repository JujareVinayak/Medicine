/*
*Lowe's Companies Inc., Android Application
* Copyright (C)  Lowe's Companies Inc.
*
*  The Lowe's Application is the private property of
*  Lowe's Companies Inc. Any distribution of this software
*  is unlawful and prohibited.
*/
package com.vinayak.medicine.presentation

import com.google.gson.Gson
import org.junit.Before
import org.mockito.Mockito
import org.mockito.MockitoAnnotations.openMocks

/**
 * Created by Raghavendra D G on 19/03/20.
 *
 * Copyright (C) 2020 Lowe's Companies Inc.
 *
 *  This application is the private property of Lowe's Companies Inc.
 *  Any distribution of this software is unlawful and prohibited.
 */
open class BaseTest {

    @Before
    open fun setup() {
        openMocks(this)
    }

    fun <T> any(): T = Mockito.any<T>()
}