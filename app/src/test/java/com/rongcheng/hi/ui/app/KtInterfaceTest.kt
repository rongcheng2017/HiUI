package com.rongcheng.hi.ui.app

import com.rongcheng.hi.ui.tab.bottom.HiTabBottom
import com.rongcheng.hi.ui.tab.bottom.HiTabBottomInfo
import com.rongcheng.hi.ui.tab.common.IHiTab
import com.rongcheng.hi.ui.tab.common.OnTabSelectedListener
import org.junit.Test

class KtInterfaceTest {
    @Test
    fun personListAddBaby_True() {
        val personList = ArrayList<Person>()
        personList.add(Baby())
    }

    @Test
    fun runnerListAddBaby_True() {
        val runnerList = ArrayList<Runner>()
        runnerList.add(Baby())
    }

    @Test
    fun malasongListAddBaby_True(){
        val malasongList = ArrayList<Malasong>()
        malasongList.add(Baby())
    }
    @Test
    fun HiTabBottomLayout_List_add_true(){
        val tabSelectedChangeListeners =
            ArrayList<OnTabSelectedListener<HiTabBottomInfo<*>>>()
        tabSelectedChangeListeners.add(TestHiTabBottom())
    }
}

class TestHiTabImplementOnTabChangeListener : OnTabSelectedListener<HiTabBottomInfo<*>>{
    override fun onTabSelectedChange(
        index: Int,
        prevInfo: HiTabBottomInfo<*>,
        nextInfo: HiTabBottomInfo<*>
    ) {

    }

}
class TestHiTabBottom :IHiTab<HiTabBottomInfo<*>>{
    override fun setHiTabInfo(data: HiTabBottomInfo<*>) {
        TODO("Not yet implemented")
    }

    override fun resetHeight(height: Int) {
        TODO("Not yet implemented")
    }

    override fun onTabSelectedChange(
        index: Int,
        prevInfo: HiTabBottomInfo<*>,
        nextInfo: HiTabBottomInfo<*>
    ) {
        TODO("Not yet implemented")
    }


}
abstract class Person {
    abstract fun eat()
}

interface Runner :Malasong{
    fun run()
}

interface  Malasong{
    fun malasong()
}

class Baby : Person(), Runner {
    override fun eat() {
        println("baby can eat")
    }

    override fun run() {
        println("haha  i can run fast")
    }

    override fun malasong() {
        TODO("Not yet implemented")
    }
}
