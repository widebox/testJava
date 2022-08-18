package one.widebox.showdemo.services;

import org.apache.tapestry5.SelectModel;

public interface SelectModelService {

	SelectModel getProvinceModel();

	SelectModel getCityModel(Long provinceId);

}
