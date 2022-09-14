package com.vinayak.medicine.presentation

import org.junit.Before
import org.mockito.MockitoAnnotations.openMocks

open class BaseTest {

    @Before
    open fun setup() {
        openMocks(this)
    }

}