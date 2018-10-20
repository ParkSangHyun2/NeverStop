

package neverstop.manager.entity.sensor;

/**
 * PowerBalance
 *
 * @author @author <a href="mailto:mhjang@nextree.co.kr">Jang, Mihyeon</a>
 * @since 08/09/2018
 */
public enum PowerBalance {
    //
    Full(2), HalfFull(1), Danger(0);

    private int code;

    PowerBalance(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static PowerBalance valueOf(int code) {
        for (PowerBalance powerBalance : PowerBalance.values()) {
            if (powerBalance.getCode() == code) {
                return powerBalance;
            }
        }

        return null;
    }

    public static PowerBalance markState(double powerBalance) {
        //
        if (powerBalance > 90) {
            return Full;
        } else if (powerBalance > 50) {
            return HalfFull;
        } else {
            return Danger;
        }
    }
}
