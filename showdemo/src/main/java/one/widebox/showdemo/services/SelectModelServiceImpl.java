package one.widebox.showdemo.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import one.widebox.foggyland.tapestry5.DefaultSelectModel;
import one.widebox.foggyland.tapestry5.hibernate.services.Dao;
import one.widebox.showdemo.entities.City;
import one.widebox.showdemo.entities.Province;

public class SelectModelServiceImpl implements SelectModelService {

	@Inject
	private Dao dao;

	private <T extends OptionModel> SelectModel getModel(Class<T> target, Order order) {
		return getModel(target, new ArrayList<Criterion>(), order);
	}

	private <T extends OptionModel> SelectModel getModel(Class<T> target, List<? extends Criterion> criterions,
			Order order) {
		return new DefaultSelectModel(dao.list(target, criterions, order));
	}

	@Override
	public SelectModel getProvinceModel() {
		return getModel(Province.class, Order.asc("seq"));
	}

	@Override
	public SelectModel getCityModel(Long provinceId) {
		List<Criterion> crits = new ArrayList<Criterion>();
		if (provinceId != null) {
			crits.add(Restrictions.eq("province.id", provinceId));
		}
		return getModel(City.class, crits, Order.asc("seq"));
	}
}
