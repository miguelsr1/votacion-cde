/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.hbar.HorizontalBarChartDataSet;
import org.primefaces.model.charts.hbar.HorizontalBarChartModel;
import org.primefaces.model.charts.optionconfig.title.Title;
import sv.gob.mined.app.facade.CatalogoFacade;
import sv.gob.mined.app.model.Cargo;
import sv.gob.mined.app.model.ProcesoVotacion;
import sv.gob.mined.app.model.dto.DistribucionVotacionDto;
import sv.gob.mined.utils.jsf.JsfUtil;

/**
 *
 * @author misanchez
 */
@Named
@ViewScoped
public class ResultadoVotacionView implements Serializable {

    private Integer idCargo;
    private String nombramiento;
    private ProcesoVotacion procesoVotacion;

    private List<DistribucionVotacionDto> lstDistribucionFinalPorcentaje = new ArrayList();

    //private HorizontalBarChartModel hbarModel;
    @Inject
    private CatalogoFacade catalogoFacade;
    @Inject
    private ParametrosSesionView parametrosSesionView;

    @PostConstruct
    public void init() {
        procesoVotacion = parametrosSesionView.getProcesoVotacion();
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public String getNombramiento() {
        return nombramiento;
    }

    public void setNombramiento(String nombramiento) {
        this.nombramiento = nombramiento;
    }

    public List<DistribucionVotacionDto> lstDistribucionFinalPorcentaje(Integer idCargo) {
        lstDistribucionFinalPorcentaje.clear();
        if (idCargo != null) {
            lstDistribucionFinalPorcentaje = catalogoFacade.getDistribucionVotosFinalPorcentaje(procesoVotacion.getIdProcesoVotacion(), idCargo, nombramiento);
        }

        return lstDistribucionFinalPorcentaje;
    }

    public List<Cargo> getLstCargo() {
        return catalogoFacade.findAllCargos(procesoVotacion.getIdProcesoVotacion());
    }

    public void limpiar() {
        lstDistribucionFinalPorcentaje.clear();
        nombramiento = null;
    }

    public void filtrar() {
        lstDistribucionFinalPorcentaje.clear();
        if (idCargo != null) {
            lstDistribucionFinalPorcentaje = catalogoFacade.getDistribucionVotosFinalPorcentaje(procesoVotacion.getIdProcesoVotacion(), idCargo, nombramiento);
        } else {
            JsfUtil.mensajeAlerta("Por favor seleccionar valores válidos");
        }
    }

    /*public void createHorizontalBarModel(Integer idCargo) {
        HorizontalBarChartModel hbarModel;
        hbarModel = new HorizontalBarChartModel();
        ChartData data = new ChartData();

        HorizontalBarChartDataSet hbarDataSet = new HorizontalBarChartDataSet();
        hbarDataSet.setLabel("My First Dataset");

        List<Number> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        List<String> bgColor = new ArrayList<>();
        List<String> borderColor = new ArrayList<>();

        lstDistribucionFinalPorcentaje(idCargo);

        int min = 50;
        int max = 255;

        for (DistribucionVotacionDto dis : lstDistribucionFinalPorcentaje) {
            values.add(dis.getCantidad());
            labels.add(dis.getNombres().concat(" ").concat(dis.getApellidos()));

            int valor1 = (int) Math.floor(Math.random() * (max - min + 1) + min);
            int valor2 = (int) Math.floor(Math.random() * (max - min + 1) + min);
            int valor3 = (int) Math.floor(Math.random() * (max - min + 1) + min);

            bgColor.add("rgba(" + valor1 + ", " + valor2 + ", " + valor3 + ", 0.2)");
            borderColor.add("rgb(" + valor1 + ", " + valor2 + ", " + valor3 + ")");
        }

        hbarDataSet.setData(values);

        hbarDataSet.setBackgroundColor(bgColor);
        hbarDataSet.setBorderColor(borderColor);
        hbarDataSet.setBorderWidth(1);

        data.addChartDataSet(hbarDataSet);

        data.setLabels(labels);
        hbarModel.setData(data);

        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addXAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("Horizontal Bar Chart");
        options.setTitle(title);

        hbarModel.setOptions(options);
    }*/
    public HorizontalBarChartModel hbarModel(Integer idCargo, String descripcionCargo) {
        HorizontalBarChartModel hbarModel;
        hbarModel = new HorizontalBarChartModel();
        ChartData data = new ChartData();

        HorizontalBarChartDataSet hbarDataSet = new HorizontalBarChartDataSet();
        hbarDataSet.setLabel((idCargo % 2 == 0) ? "Suplente" : "Propietario");

        List<Number> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        List<String> bgColor = new ArrayList<>();
        List<String> borderColor = new ArrayList<>();

        lstDistribucionFinalPorcentaje(idCargo);

        int min = 50;
        int max = 255;

        for (DistribucionVotacionDto dis : lstDistribucionFinalPorcentaje) {
            values.add(dis.getCantidad());
            labels.add(dis.getNombres().concat(" ").concat(dis.getApellidos()));

            int valor1 = (int) Math.floor(Math.random() * (max - min + 1) + min);
            int valor2 = (int) Math.floor(Math.random() * (max - min + 1) + min);
            int valor3 = (int) Math.floor(Math.random() * (max - min + 1) + min);

            bgColor.add("rgba(" + valor1 + ", " + valor2 + ", " + valor3 + ", 0.2)");
            borderColor.add("rgb(" + valor1 + ", " + valor2 + ", " + valor3 + ")");
        }

        hbarDataSet.setData(values);

        hbarDataSet.setBackgroundColor(bgColor);
        hbarDataSet.setBorderColor(borderColor);
        hbarDataSet.setBorderWidth(1);

        data.addChartDataSet(hbarDataSet);

        data.setLabels(labels);
        hbarModel.setData(data);

        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addXAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText(descripcionCargo);
        options.setTitle(title);

        hbarModel.setOptions(options);

        return hbarModel;
    }

}
