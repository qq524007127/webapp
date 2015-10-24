package com.sunjee.btms.common;

import java.util.Comparator;

import com.sunjee.component.bean.Module;

public class ModuleComparator implements Comparator<Module> {

	@Override
	public int compare(Module m1, Module m2) {
		return m1.getModuleSort() - m2.getModuleSort();
	}

}
