package teamethernet.senml_api;

import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SenMLAPITest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void json_many_parameters() throws JsonProcessingException {
		final SenMLAPI senMLAPI = SenMLAPI.initJsonEncode();
		Pair bn = new Pair<>(Label.BASE_NAME, "mac:urn:dev:3290329032");
		Pair v = new Pair<>(Label.VALUE, 30.00);
		Pair vb = new Pair<>(Label.BOOLEAN_VALUE, false);
		Pair u = new Pair<>(Label.UNIT, "dB");
		senMLAPI.addRecord(bn, v, vb, u);
		assertEquals("[{\"bn\":\"mac:urn:dev:3290329032\",\"v\":30.0,\"vb\":false,\"u\":\"dB\"}]", senMLAPI.endSenML());
    }

    @Test
    public void json_multiple_records() throws JsonProcessingException {
		final SenMLAPI senMLAPI = SenMLAPI.initJsonEncode();
		Pair bn1 = new Pair<>(Label.BASE_NAME, "mac:urn:dev:3290329032");
		Pair bn2 = new Pair<>(Label.BASE_NAME, "mac:urn:dev:329032942");
		Pair bver = new Pair<>(Label.BASE_VERSION, 0);
		Pair vs = new Pair<>(Label.STRING_VALUE, "hello");
		Pair ut = new Pair<>(Label.UPDATE_TIME, 30.00);

		senMLAPI.addRecord(bn1, bver);
		senMLAPI.addRecord(bn2, vs, ut);
		assertEquals("[{\"bn\":\"mac:urn:dev:3290329032\",\"bver\":0},{\"bn\":\"mac:urn:dev:329032942\",\"vs\":\"hello\",\"ut\":30.0}]", senMLAPI.endSenML());
    }


}