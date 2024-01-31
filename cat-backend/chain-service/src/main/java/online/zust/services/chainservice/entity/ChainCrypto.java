package online.zust.services.chainservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chainmaker.pb.config.ChainConfigOuterClass;

/**
 * @author qcqcqc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChainCrypto {
    private String hash;

    public ChainCrypto(ChainConfigOuterClass.CryptoConfig crypto) {
        this.hash = crypto.getHash();
    }
}
