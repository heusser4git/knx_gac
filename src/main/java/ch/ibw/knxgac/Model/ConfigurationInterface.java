package ch.ibw.knxgac.Model;

import java.io.Serializable;

public interface ConfigurationInterface {
    /**
     * Checks if the configuration data are filled in complete
     * @return true = all good, false = not everything is filled in
     */
    public boolean configComplete();
}
