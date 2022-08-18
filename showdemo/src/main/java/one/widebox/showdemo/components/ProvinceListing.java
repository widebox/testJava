package one.widebox.showdemo.components;

import java.util.List;

import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import one.widebox.showdemo.entities.Province;
import one.widebox.showdemo.services.AppService;

public class ProvinceListing extends BaseComponent {

	@Component
	private MyGrid grid;
	@Inject
	private AppService appService;
	@Property
	private List<Province> rows;
	@Property
	private Province row;

	@BeginRender
	public void beginRender() {
		rows = appService.listProvince();
		if (rows.size() > 0 && grid.getSortModel().getSortConstraints().size() == 0) {
			grid.getSortModel().updateSort("seq");
		}
	}

}
