import org.nomin.Mapping

class ItemMapper extends Mapping {

    protected void build() {
        mappingFor a: ItemInfo, b: CombinedItem
        mapper.parse(ItemDataMapper)
        a.type = "Feature"
        a.name = b.name
        a.data = b
    }

}

