package one.widebox.showdemo.components;

import java.util.List;

import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import one.widebox.showdemo.entities.City;
import one.widebox.showdemo.services.AppService;

public class CityListing extends BaseComponent {

	@Component
	private MyGrid grid;
	@Inject
	private AppService appService;
	@Property
	private List<City> rows;
	@Property
	private City row;

	@BeginRender
	public void beginRender() {
		rows = appService.listCity();
		if (rows.size() > 0 && grid.getSortModel().getSortConstraints().size() == 0) {
			grid.getSortModel().updateSort("seq");
		}
	}

}
