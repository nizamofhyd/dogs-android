package com.dogs.fragments

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.dogs.di.Injectable

open class InjectedBaseFragment(@LayoutRes layoutResourceId: Int) : Fragment(layoutResourceId),
    Injectable