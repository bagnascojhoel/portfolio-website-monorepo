import * as React from 'react'

import cs from 'classnames'

import styles from './styles.module.css'

export function ReturnIcon(props) {
  const { className, ...rest } = props
  return (
    <svg
      className={cs(styles.icon, className)}
      {...rest}
      viewBox='0 0 24 24'
    >
        <path d="M5 12H19M5 12L11 6M5 12L11 18" stroke="#F97316" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
    </svg>
  )
}
