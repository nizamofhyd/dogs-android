package com.dogs.fragments

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

open class InjectedBaseFragment(
    @LayoutRes layoutResourceId: Int
) : Fragment(layoutResourceId)