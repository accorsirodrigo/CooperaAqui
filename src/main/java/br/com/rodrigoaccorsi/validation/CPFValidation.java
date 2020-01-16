package br.com.rodrigoaccorsi.validation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;

import br.com.rodrigoaccorsi.routes.util.RoutesUtils;

@Component
public class CPFValidation {

	private final String ENDPOINT = "https://user-info.herokuapp.com/users/";
	private final String ABLE_TO_VOTE = "ABLE_TO_VOTE";
	private final Boolean FAKE_CERTIFIER = true;

	private enum validateStatus {
		NOT_FOUND,
		ABLE_TO_VOTE,
		UNABLE_TO_VOTE
	};

	public String checkCpfIsAbleToVote(String CPF) {
		try {
			return check(CPF);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String check(String CPF) throws IOException {
		if(FAKE_CERTIFIER)
			doTheFakeCertifier();

		BufferedReader reader = null;
		try {
			final URL url = new URL(ENDPOINT+CPF);
			final HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			if(conn.getResponseCode() == HttpStatus.SC_NOT_FOUND) {
				return validateStatus.NOT_FOUND.name();
			} else if(conn.getResponseCode() == HttpStatus.SC_OK) {
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				JsonObject oJson = RoutesUtils.castStrJsonToObject(reader.readLine(), JsonObject.class);
				String status = oJson.get("status").getAsString(); 
				switch (status) {
				case ABLE_TO_VOTE:
					return validateStatus.ABLE_TO_VOTE.name();
				default:
					return validateStatus.UNABLE_TO_VOTE.name();
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if(reader != null) {
				reader.close();
			}
		}
	}

	private void doTheFakeCertifier() {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[]{
			new X509TrustManager() {
				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(
					java.security.cert.X509Certificate[] certs, String authType) {
				}

				@Override
				public void checkServerTrusted(
					java.security.cert.X509Certificate[] certs, String authType) {
				}
			}
		};

		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
		}
	}
}
