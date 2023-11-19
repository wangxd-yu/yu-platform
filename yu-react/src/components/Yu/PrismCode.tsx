import Prism from 'prismjs';
import { useRef, useEffect } from 'react';

interface PrismCodeProps {
    code: string;
    language: string;
    plugins?: string[];
    style?: React.CSSProperties; // 添加 style 属性
  }

const PrismCode: React.FC<PrismCodeProps> = ({ code, language, plugins = [], style }) => {
    const ref = useRef(null);
    useEffect(() => {
        if (ref && ref.current) {
            Prism.highlightElement(ref.current);
        }
    }, [code]);

    return (
        <pre className={`prism-code ${plugins.join(" ")}`} style={style}>
            <code ref={ref} className={`prism-code language-${language}`}>
                {code}
            </code>
        </pre>
    );
};

export default PrismCode;