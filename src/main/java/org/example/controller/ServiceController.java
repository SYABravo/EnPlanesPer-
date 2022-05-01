package org.example.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.example.business.CarBusiness;
import org.example.business.DepartmentBusiness;
import org.example.business.PlateBusiness;
import org.example.business.ServiceBusiness;
import org.example.business.ServiceTypeBusiness;
import org.example.business.ProvinceBusiness;
import org.example.business.RoomBusiness;
import org.example.entities.Car;
import org.example.entities.Department;
import org.example.entities.Plate;
import org.example.entities.Service;
import org.example.entities.ServiceType;
import org.example.entities.Province;
import org.example.entities.Room;
import org.example.util.Message;

@Named
@SessionScoped
public class ServiceController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ServiceBusiness serviceBusiness;
	@Inject
	private DepartmentBusiness departmentBusiness;
	@Inject
	private ProvinceBusiness provinceBusiness;
	@Inject
	private ServiceTypeBusiness serviceTypeBusiness;
	@Inject
	private RoomBusiness roomBusiness;
	@Inject
	private CarBusiness carBusiness;
	@Inject
	private PlateBusiness plateBusiness;

	// VARIABLES
	private Service service;
	private Service serviceHostingSelected;
	private Service serviceFoodSelected;
	private Service serviceTransportSelected;
	private List<Service> services;
	private List<Service> hostingServices;
	private List<Service> foodServices;
	private List<Service> transportServices;

	private Department department;
	private List<Department> departments;

	private Province province;
	private List<Province> provinces;

	private ServiceType serviceType;
	private List<ServiceType> serviceTypes;

	private List<Room> rooms;
	private Room room;

	private List<Car> cars;
	private Car car;

	private List<Plate> plates;
	private Plate plate;

	private int contador;

	// METODOS

	@PostConstruct
	public void init() {
		service = new Service();
		serviceHostingSelected = new Service();
		serviceFoodSelected = new Service();
		serviceTransportSelected = new Service();

		services = new ArrayList<>();
		hostingServices = new ArrayList<>();
		foodServices = new ArrayList<>();
		transportServices = new ArrayList<>();

		department = new Department();
		departments = new ArrayList<>();

		province = new Province();
		provinces = new ArrayList<>();

		serviceType = new ServiceType();
		serviceTypes = new ArrayList<>();

		rooms = new ArrayList<>();
		room = new Room();

		plates = new ArrayList<>();
		plate = new Plate();

		cars = new ArrayList<>();
		car = new Car();

		contador = 0;

		getAllService();
	}

	public void getAllService() {
		try {
			services = serviceBusiness.getAll();
			hostingServices = serviceBusiness.getAllHostingServices();
			foodServices = serviceBusiness.getAllFoodServices();
			transportServices = serviceBusiness.getAllTransportServices();
		} catch (Exception e) {

		}
	}

	public String newService() {
		try {
			this.departments = departmentBusiness.getAllDepartment();
			this.serviceTypes = serviceTypeBusiness.getAll();
		} catch (Exception e) {
			Message.messageError("Error ProductController:" + e.getMessage());
		}
		return "insert.xhtml";
	}

	public String newProductService() {
		String view = "";
		try {
			if (serviceType.getId() == 1) {
				view = "/services/insert-room";
			} else if (serviceType.getId() == 2) {
				view = "/services/insert-plate";
			} else if (serviceType.getId() == 3) {
				view = "/services/insert-car";
			}
		} catch (Exception e) {
			Message.messageError("Error ProductController:" + e.getMessage());
		}
		return view;
	}

	// GUARDAR PRODUCTOS

	public void saveRoom() {
		if (contador < 3) {
			this.rooms.add(room);
			this.room = new Room();
			contador++;
		} else {
			Message.messageInfo("Solo puedes agregar 3 habitaciones por servicio");
		}
	}
	
	public void saveCar() {
		if (contador < 3) {
			this.cars.add(car);
			this.car = new Car();
			contador++;
		} else {
			Message.messageInfo("Solo puedes agregar 3 carros por servicio");
		}
	}

	// GUARDAR
	public String saveService() {
		String view = "";
		try {
			if (service.getId() != null) {
				this.service.setDepartment(department);
				this.service.setProvince(province);
				this.service.setServiceType(serviceType);
				this.serviceBusiness.update(service);
				Message.messageInfo("Servicio actualizado exitosamente");
			} else {
				this.service.setStar(1);
				this.service.setDepartment(department);
				this.service.setProvince(province);
				this.service.setServiceType(serviceType);
				
				this.serviceBusiness.insert(service);
				
				if(serviceType.getId() == 1) {
					for (Room rs : rooms) {
						rs.setService(service);
						this.roomBusiness.insert(rs);
					}
				} else if (serviceType.getId() == 2) {
					plate.setService(service);
					this.plateBusiness.insert(plate);
				} else if (serviceType.getId() == 3) {
					for (Car c : cars) {
						c.setService(service);
						this.carBusiness.insert(c);
					}
				}
				

				
				Message.messageInfo("Servicio agregado exitosamente");
			}
			this.getAllService();
			this.resetForm();
			view = "/services/list";
		} catch (Exception e) {
			Message.messageError("Error ProductController:" + e.getMessage());
		}
		return view;
	}

	public String editHostingService() {
		String view = "";
		try {
			if (this.serviceHostingSelected != null) {
				this.service = serviceHostingSelected;
				this.departments = departmentBusiness.getAllDepartment();
				this.provinces = provinceBusiness.getAllProvince();
				this.serviceTypes = serviceTypeBusiness.getAll();
				view = "/services/insert";
			} else {
				Message.messageInfo("Debe seleccionar un servicio");
			}
		} catch (Exception e) {
			Message.messageError("Error ProductController:" + e.getMessage());
		}
		return view;
	}

	public String editFoodService() {
		String view = "";
		try {
			if (this.serviceFoodSelected != null) {
				this.service = serviceFoodSelected;
				this.departments = departmentBusiness.getAllDepartment();
				this.provinces = provinceBusiness.getAllProvince();
				this.serviceTypes = serviceTypeBusiness.getAll();
				view = "/services/insert";
			} else {
				Message.messageInfo("Debe seleccionar un servicio");
			}
		} catch (Exception e) {
			Message.messageError("Error ProductController:" + e.getMessage());
		}
		return view;
	}

	public String editTransportService() {
		String view = "";
		try {
			if (this.serviceTransportSelected != null) {
				this.service = serviceTransportSelected;
				this.departments = departmentBusiness.getAllDepartment();
				this.provinces = provinceBusiness.getAllProvince();
				this.serviceTypes = serviceTypeBusiness.getAll();
				view = "/services/insert";
			} else {
				Message.messageInfo("Debe seleccionar un servicio");
			}
		} catch (Exception e) {
			Message.messageError("Error ProductController:" + e.getMessage());
		}
		return view;
	}

	public String deleteHostingService() {
		String view = "";
		try {
			if (this.serviceHostingSelected != null) {
				this.service = serviceHostingSelected;
				serviceBusiness.delete(service);
				getAllService();
				view = "/services/list";
			} else {
				Message.messageInfo("Debe seleccionar un servicio");
			}
		} catch (Exception e) {
			Message.messageError("Error ProductController:" + e.getMessage());
		}
		return view;
	}

	public String deleteFoodService() {
		String view = "";
		try {
			if (this.serviceFoodSelected != null) {
				this.service = serviceFoodSelected;
				serviceBusiness.delete(service);
				getAllService();
				view = "/services/list";
			} else {
				Message.messageInfo("Debe seleccionar un servicio");
			}
		} catch (Exception e) {
			Message.messageError("Error ProductController:" + e.getMessage());
		}
		return view;
	}

	public String deleteTransportService() {
		String view = "";
		try {
			if (this.serviceTransportSelected != null) {
				this.service = serviceTransportSelected;
				serviceBusiness.delete(service);
				getAllService();
				view = "/services/list";
			} else {
				Message.messageInfo("Debe seleccionar un servicio");
			}
		} catch (Exception e) {
			Message.messageError("Error ProductController:" + e.getMessage());
		}
		return view;
	}

	public void resetForm() {
		service = new Service();
		serviceHostingSelected = new Service();
		serviceFoodSelected = new Service();
		serviceTransportSelected = new Service();
		
		rooms = new ArrayList<>();
		room = new Room();
		
		contador = 0;
	}

	public String listService() {
		this.resetForm();
		return "list.xhtml";
	}

	public void provinceChange() {
		try {
			Message.messageInfo("Se actualizo");
			this.provinces = provinceBusiness.getAllProvinceByDepartment(department.getId());
		} catch (Exception e) {
			Message.messageInfo("Error en revoger datos");
		}
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	public List<Province> getProvinces() {
		return provinces;
	}

	public void setProvinces(List<Province> provinces) {
		this.provinces = provinces;
	}

	public List<Service> getHostingServices() {
		return hostingServices;
	}

	public void setHostingServices(List<Service> hostingServices) {
		this.hostingServices = hostingServices;
	}

	public List<Service> getFoodServices() {
		return foodServices;
	}

	public void setFoodServices(List<Service> foodServices) {
		this.foodServices = foodServices;
	}

	public List<Service> getTransportServices() {
		return transportServices;
	}

	public void setTransportServices(List<Service> transportServices) {
		this.transportServices = transportServices;
	}

	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public List<ServiceType> getServiceTypes() {
		return serviceTypes;
	}

	public void setServiceTypes(List<ServiceType> serviceTypes) {
		this.serviceTypes = serviceTypes;
	}

	public Service getServiceHostingSelected() {
		return serviceHostingSelected;
	}

	public void setServiceHostingSelected(Service serviceHostingSelected) {
		this.serviceHostingSelected = serviceHostingSelected;
	}

	public Service getServiceFoodSelected() {
		return serviceFoodSelected;
	}

	public void setServiceFoodSelected(Service serviceFoodSelected) {
		this.serviceFoodSelected = serviceFoodSelected;
	}

	public Service getServiceTransportSelected() {
		return serviceTransportSelected;
	}

	public void setServiceTransportSelected(Service serviceTransportSelected) {
		this.serviceTransportSelected = serviceTransportSelected;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public List<Plate> getPlates() {
		return plates;
	}

	public void setPlates(List<Plate> plates) {
		this.plates = plates;
	}

	public Plate getPlate() {
		return plate;
	}

	public void setPlate(Plate plate) {
		this.plate = plate;
	}

	public int getContador() {
		return contador;
	}

	public void setContador(int contador) {
		this.contador = contador;
	}

}
