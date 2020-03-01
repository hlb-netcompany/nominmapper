import org.nomin.Mapping

import static Itemtype.INVALID_TYPE

class ItemDataMapper extends Mapping {

    protected void build() {
        mappingFor a: ItemData, b: CombinedItem
        mapNulls = true

        a.datafield = b.data
        a.type = b.type
        convert to_a: { b_type -> b_type == INVALID_TYPE ? null : b_type.toString() },
                to_b: { a_type -> Itemtype.values().find { it.name() == a_type } ?: INVALID_TYPE }

        /*
        I advise to use a custom converter in such a case instead of using expressions at both sides as more effective
        way of parsing and mapping data. Nevertheless the following expressions work as expected
        a.type = {
            b.type == INVALID_TYPE ? null : b.type.toString()
        }
        b.type = {
            Itemtype.values().find { it.name() == a.type } ?: INVALID_TYPE
        }
        */
    }
}
