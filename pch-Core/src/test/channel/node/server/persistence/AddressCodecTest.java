package papyrus.channel.node.server.persistence;

import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.web3j.abi.datatypes.Address;
import org.web3j.utils.Numeric;

import com.datastax.driver.core.ProtocolVersion;

public class AddressCodecTest {

    private Random random = new Random(LocalDate.now().toEpochDay());

    @Test
    public void testSerialize() {
        Address address = randomAddress();
        AddressCodec codec = new AddressCodec();
        ByteBuffer buffer = codec.serialize(address, ProtocolVersion.NEWEST_SUPPORTED);
        Address address1 = codec.deserialize(buffer, ProtocolVersion.NEWEST_SUPPORTED);
        Assert.assertEquals(address, address1);
    }

    @Test
    public void testFormat() {
        Address address = randomAddress();
        AddressCodec codec = new AddressCodec();
        String str = codec.format(address);
        Address address1 = codec.parse(str);
        Assert.assertEquals(address, address1);
    }

    private Address randomAddress() {
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        return new Address(Numeric.toBigInt(bytes));
    }
}