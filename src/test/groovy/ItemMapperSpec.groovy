import org.nomin.NominMapper
import org.nomin.core.Nomin
import spock.lang.Specification
import spock.lang.Unroll

import static Itemtype.*

class ItemMapperSpec extends Specification {
    NominMapper mapper = new Nomin(ItemMapper, ItemDataMapper).disableCache()

    @Unroll
    void "mapper from ItemInfo->CombinedItem converts itemtype correctly"() {
        given:
            ItemInfo convertFrom = new ItemInfo(
                name: 'a good name',
                type: "Feature",
                data: new ItemData(
                    datafield: 'this is my data dump',
                    type: leftsideType
                )
            )
        when:
            CombinedItem convertTo = mapper.map(convertFrom, CombinedItem)
        then:
            convertTo.name == convertFrom.name
            convertTo.data == convertFrom.data.datafield
            convertTo.type == expectedType
        where:
            leftsideType || expectedType
            'TYPE_A'     || TYPE_A
            'TYPE_B'     || TYPE_B
            ''           || INVALID_TYPE
            null         || INVALID_TYPE
    }

    @Unroll
    void "mapper from CombinedItem->ItemInfo converts itemtype correctly"() {
        given:
            CombinedItem convertFrom = new CombinedItem(
                name: 'a good name',
                data: 'this is my data dump',
                type: rightSideType
            )
        when:
            ItemInfo convertTo = mapper.map(convertFrom, ItemInfo)
        then:
            convertTo.name == convertFrom.name
            convertTo.type == "Feature"
            convertTo.data.datafield == convertFrom.data
            convertTo.data.type == expectedType
        where:
            rightSideType || expectedType
            TYPE_A        || 'TYPE_A'
            TYPE_B        || 'TYPE_B'
            INVALID_TYPE  || null
    }
}
