import difference from 'lodash/difference';
import { Table, Transfer } from 'antd';
import type { ColumnsType, TableRowSelection } from 'antd/es/table/interface';
import type { TransferItem, TransferProps } from 'antd/es/transfer';

interface TableTransferProps<DataType extends TransferItem> extends TransferProps<DataType> {
    dataSource: DataType[];
    leftColumns: ColumnsType<DataType>;
    rightColumns: ColumnsType<DataType>;
}

const TableTransfer = <DataType extends TransferItem>({
    leftColumns,
    rightColumns,
    ...restProps
}: TableTransferProps<DataType>) => {

    return (
        <Transfer
            {...restProps}
        >
            {({
                direction,
                filteredItems,
                onItemSelectAll,
                onItemSelect,
                selectedKeys: listSelectedKeys,
                disabled: listDisabled,
            }) => {
                const columns = direction === 'left' ? leftColumns : rightColumns;

                const rowSelection: TableRowSelection<TransferItem> = {
                    getCheckboxProps: (item) => ({ disabled: listDisabled || item.disabled }),
                    onSelectAll(selected, selectedRows) {
                        const treeSelectedKeys = selectedRows
                            .filter((item) => !item.disabled)
                            .map(({ key }) => key);
                        const diffKeys = selected
                            ? difference(treeSelectedKeys, listSelectedKeys)
                            : difference(listSelectedKeys, treeSelectedKeys);
                        onItemSelectAll(diffKeys as string[], selected);
                    },
                    onSelect({ key }, selected) {
                        onItemSelect(key as string, selected);
                    },
                    selectedRowKeys: listSelectedKeys,
                };

                return (<Table
                    columns={columns as ColumnsType<any>}
                    dataSource={filteredItems}
                    size="small"
                    pagination={false}
                    rowSelection={rowSelection} /* 将 rowSelection 属性放在 Table 组件上 */
                    style={{ pointerEvents: restProps.disabled ? 'none' : undefined }}
                    onRow={({ key, disabled: itemDisabled }) => ({
                        onClick: () => {
                            if (itemDisabled || restProps.disabled) return;
                            onItemSelect(key as string, !listSelectedKeys.includes(key as string));
                        },
                    })}
                />)
            }}
        </Transfer>
    );
};

export default TableTransfer;
