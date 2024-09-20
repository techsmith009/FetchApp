package com.example.fetchapp.model

import com.example.fetchapp.utility.StringWrapper
import com.example.fetchapp.utility.asStringWrapper

data class ServiceException(val code: String?, val string: StringWrapper) {
    constructor(code: String?, string: String) : this(code, string.asStringWrapper())
}

