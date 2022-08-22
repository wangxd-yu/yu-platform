import React, { useState } from 'react'
import { ModalForm } from '@ant-design/pro-form'
import type { ModalFormProps } from '@ant-design/pro-form';
import Draggable from 'react-draggable';

const YuDraggleModal: React.FC<ModalFormProps> = ((props: ModalFormProps) => {
    const [disabled, setDisabled] = useState<boolean>(false);
    const [bounds, setBounds] = useState<any>();
    const draggleRef = React.createRef<HTMLDivElement>();

    const { title, modalProps, children, ...proFormProps } = props

    const onStart = (event: any, uiData: { x: number; y: number; }) => {
        const { clientWidth, clientHeight } = window.document.documentElement;
        const targetRect = draggleRef?.current?.getBoundingClientRect();
        if (!targetRect) {
            return;
        }
        setBounds({
            left: -targetRect.left + uiData.x,
            right: clientWidth - (targetRect.right - uiData.x),
            top: -targetRect.top + uiData.y,
            bottom: clientHeight - (targetRect.bottom - uiData.y),
        });
    };

    return (
        <ModalForm
            title={
                <div
                    style={{
                        width: '100%',
                        cursor: 'move',
                    }
                    }
                    onMouseOver={() => {
                        if (disabled) {
                            setDisabled(false)
                        }
                    }}
                    onMouseOut={() => {
                        setDisabled(true)
                    }}
                    // fix eslintjsx-a11y/mouse-events-have-key-events
                    // https://github.com/jsx-eslint/eslint-plugin-jsx-a11y/blob/master/docs/rules/mouse-events-have-key-events.md
                    onFocus={() => { }}
                    onBlur={() => { }}
                // end
                >
                    {title}
                </div>
            }
            {...proFormProps}
            modalProps={{
                modalRender: (modal: boolean | React.ReactChild | React.ReactFragment | React.ReactPortal | null | undefined) => (
                    <Draggable
                        disabled={disabled}
                        bounds={bounds}
                        onStart={(event, uiData) => onStart(event, uiData)}
                    >
                        <div ref={draggleRef}> {modal} </div>
                    </Draggable>
                )
            }}
        >
            {children}
        </ModalForm>)
})

export default YuDraggleModal;