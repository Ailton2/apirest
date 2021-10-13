package br.com.apirest.service;

import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Service
public class ServiceRelatorio implements Serializable {


	private static final long serialVersionUID = 1L;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public byte[] gerarRelatorio (String nomeRelatorio, ServletContext servletContext) throws Exception {
	//obter a conexao
    Connection  connection = jdbcTemplate.getDataSource().getConnection();
    
    //carregar o caminho 
    String caminhoJasper = servletContext.getRealPath("relatorios")
    		+ File.separator + nomeRelatorio + ".jasper";
    
    //gerar o relatorio
    JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoJasper,new HashMap<>(), connection);
    
    byte[] retorno = JasperExportManager.exportReportToPdf(jasperPrint);
    //exporta para bte o pdf para fazer o download
    connection.close();
    return retorno;
	}

}
