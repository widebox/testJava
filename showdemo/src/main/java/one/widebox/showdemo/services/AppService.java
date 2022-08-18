package one.widebox.showdemo.services;

import java.util.List;

import one.widebox.showdemo.entities.City;
import one.widebox.showdemo.entities.Province;

public interface AppService {

	void saveOrUpdate(Province province);

	void saveOrUpdate(City city);

	Province findProvince(Long id);

	City findCity(Long id);

	List<Province> listProvince();

	List<City> listCity();

	void deleteProvince(Long id);

	void deleteCity(Long id);

	boolean isProvinceSeqRepetead(Province province);

	boolean isProvinceNameRepetead(Province province);

	boolean isCityNameRepetead(City city);

}
