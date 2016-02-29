package de.onvif;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.xml.soap.SOAPException;

import org.apache.commons.io.FileUtils;
import org.onvif.ver10.events.wsdl.GetEventProperties;
import org.onvif.ver10.events.wsdl.GetEventPropertiesResponse;
import org.onvif.ver10.schema.Capabilities;
import org.onvif.ver10.schema.CapabilityCategory;
import org.onvif.ver10.schema.MediaUri;
import org.onvif.ver10.schema.Profile;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.onvif.soap.OnvifDevice;

public class MiscTests {

	public static void main(String args[]) throws MalformedURLException {
		String deviceIp, user, password,profileToken;

//		deviceIp = "192.168.1.53";
		deviceIp = "localhost:8080";
		user=password="admin";
		profileToken = "MediaProfile000";//MediaProfile_Channel1_MainStream
//		profileToken = "MediaProfile001";//MediaProfile_Channel1_SubStream1
		System.out.println("Connect to camera, please wait ...");
		
		OnvifDevice cam = null;
		try {
			cam = new OnvifDevice(deviceIp, user, password);
		}
		catch (ConnectException | SOAPException e1) {
			System.err.println("No connection to device with ip "+deviceIp+", please try again.");
			System.exit(0);
		}
		System.out.println("Connected to device "+cam.getDeviceInfo());

//		Capabilities cap = cam.getDevice().getCapabilities(Arrays.asList(CapabilityCategory.ALL));
		//profiles
		printProfiles(cam);
//		takeScreenShot(profileToken, cam);
		//presets
//		List<PTZPreset> presets = cam.getPtz().getPresets(profileToken);
//		presets.forEach(x->System.out.println(x.getName()));
		
//		GetEventProperties getEventProperties = new GetEventProperties();
//		//		List<Service> services = cam.getDevices().getServices(false);
////		services.forEach(x->System.out.println(x.getCapabilities()));
//		GetEventPropertiesResponse resp = cam.getEvents().getEventProperties(getEventProperties );
//		resp.getMessageContentFilterDialect().forEach(x->System.out.println(x));
//		resp.getTopicExpressionDialect().forEach(x->System.out.println(x));
//		List<Object> any = resp.getTopicSet().getAny();
//		for (Object object : any) {
//			Element e= (Element) object;
//			printTree(e, e.getNodeName());
//		}
		
		
//		CreatePullPointSubscription subscription = new CreatePullPointSubscription();
//		FilterType filter = new FilterType();
//		filter
//		subscription.setFilter(filter);
//		cam.getEvents().createPullPointSubscription(subscription);
	}

	private static void printTree(Node node, String name) {
		if(node.hasChildNodes()){
			NodeList nodes = node.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node n = nodes.item(i);

				printTree(n, name + " - " + n.getNodeName());
			}
		}else
			System.out.println(name + " - "+node.getNodeName());
	}

	private static void takeScreenShot(String profileToken, OnvifDevice cam) {
		try {
			MediaUri sceenshotUri = cam.getMedia().getSnapshotUri(profileToken);
			File tempFile = File.createTempFile("bosc", ".jpg");
//			tempFile.deleteOnExit();
			FileUtils.copyURLToFile(new URL(sceenshotUri.getUri()), tempFile);
			Runtime.getRuntime().exec("nautilus "+tempFile.getAbsolutePath());
			Thread.sleep(10000);
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private static void printProfiles(OnvifDevice cam)  {
		
		List<Profile> profiles = cam.getMedia().getProfiles();
		for (Profile p : profiles) {
			System.out.println("URL from Profile \'" + p.getName() + "\': " + cam.getMedia().getSnapshotUri(p.getToken()));
		}
	}
	 // This handler will add the authentication parameters
}