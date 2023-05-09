import Prism from 'prismjs';
import { useRef, useEffect } from 'react';

const PrismCode = (({ code, language, plugins = [] }: { code: string, language: string, plugins: string[] }) => {
    const ref = useRef(null);
    useEffect(() => {
        if (ref && ref.current) {
            Prism.highlightElement(ref.current);
        }
    }, [code]);

    return (
        <pre className={plugins.join(" ")}>
            <code ref={ref} className={`prism-code language-${language}`}>
                {code}
            </code>
        </pre>
    );
})

export default PrismCode;