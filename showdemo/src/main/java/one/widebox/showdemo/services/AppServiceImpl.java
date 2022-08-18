package one.widebox.showdemo.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import one.widebox.foggyland.tapestry5.hibernate.services.Dao;
import one.widebox.showdemo.entities.City;
import one.widebox.showdemo.entities.Province;

public class AppServiceImpl implements AppService {

	@Inject
	private Dao dao;

	@Override
	public void saveOrUpdate(Province province) {
		dao.saveOrUpdate(province);
	}

	@Override
	public void saveOrUpdate(City city) {
		dao.saveOrUpdate(city);
	}

	@Override
	public Province findProvince(Long id) {
		return dao.findById(Province.class, id);
	}

	@Override
	public City findCity(Long id) {
		return dao.findById(City.class, id);
	}

	@Override
	public List<Province> listProvince() {
		return dao.list(Province.class, Order.asc("seq"));
	}

	@Override
	public List<City> listCity() {
		return dao.list(City.class, Order.asc("seq"));
	}

	@Override
	public void deleteProvince(Long id) {
		dao.delete(Province.class, id);
	}

	@Override
	public void deleteCity(Long id) {
		dao.delete(City.class, id);
	}

	@Override
	public boolean isProvinceSeqRepetead(Province province) {
		return dao.isPropertyValueDuplicated(Province.class, province.getId(), "seq", province.getSeq());
	}

	@Override
	public boolean isProvinceNameRepetead(Province province) {
		return dao.isPropertyValueDuplicated(Province.class, province.getId(), "name", province.getName());
	}

	@Override
	public boolean isCityNameRepetead(City city) {
		List<Criterion> crits = new ArrayList<Criterion>();
		crits.add(Restrictions.eq("name", city.getName()));
		crits.add(Restrictions.eq("province.id", city.getProvinceId()));
		if (city.getId() != null) {
			crits.add(Restrictions.ne("id", city.getId()));
		}
		return dao.count(City.class, crits) > 0;
	}
}
