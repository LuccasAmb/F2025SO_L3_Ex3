package controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DistroController {

	public DistroController() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	private String getOS() {
		return System.getProperty("os.name");
	}
	
	public void exibeDistro() {
		String os = getOS();
		
		if(os.contains("Linux")) {
			String[] dist = readProcess(callProcess("cat /etc/os-release")).split("\\R");
			String name = "";
			String version = "";
			for(String linha: dist) {
				if(linha.startsWith("NAME=")){
					name = linha.split("\"")[1];
				}
				if(linha.startsWith("VERSION=")){
					version = linha.split("\"")[1];
				}
			}
			System.out.println("Nome do SO: " + name + "; Versão: " + version);
			
		}else {
			System.out.println("SO não é o Linux.");
		}
	}
	
	public Process callProcess(String proc) {
		String[] procArr = proc.split(" ");
		try {
			return Runtime.getRuntime().exec(procArr);
		} catch (Exception e) {
			if (e.getMessage().contains("740")) {
				StringBuffer buffer = new StringBuffer();
				buffer.append("cmd /c");
				buffer.append(" ");
				buffer.append(proc);
				try {
					procArr = buffer.toString().split(" ");
					return Runtime.getRuntime().exec(procArr);
				} catch (Exception e1) {
					System.err.println(e1.getMessage());
				}
			} else {
				System.err.println(e.getMessage());

			}
			return null;
		}
	}

	public String readProcess(Process proc) {
		try {
			InputStream fluxo = proc.getInputStream();
			InputStreamReader leitor = new InputStreamReader(fluxo);
			BufferedReader buffer = new BufferedReader(leitor);
			StringBuffer procMsg = new StringBuffer();
			String linha;
			while ((linha = buffer.readLine()) != null) {
				procMsg.append(linha).append("\n");
			}
			buffer.close();
			leitor.close();
			fluxo.close();
			return procMsg.toString();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}

	}

}
